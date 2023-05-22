package com.ayenijeremiaho.pastebinapi.pasteText.service.implementation;

import com.ayenijeremiaho.pastebinapi.employee.model.Employee;
import com.ayenijeremiaho.pastebinapi.employee.service.EmployeeService;
import com.ayenijeremiaho.pastebinapi.exception.AuthorizationException;
import com.ayenijeremiaho.pastebinapi.exception.NotFoundException;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.CreatePasteTextDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.PasteTextDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.PasteTextDTOList;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.UpdatePasteTextDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExpirationEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExposureEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.model.PasteText;
import com.ayenijeremiaho.pastebinapi.pasteText.model.PasteTextTag;
import com.ayenijeremiaho.pastebinapi.pasteText.repository.PasteTextRepository;
import com.ayenijeremiaho.pastebinapi.pasteText.repository.PasteTextTagRepository;
import com.ayenijeremiaho.pastebinapi.pasteText.service.PasteTextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasteTextServiceImpl implements PasteTextService {

    private final EmployeeService employeeService;
    private final PasteTextRepository pasteTextRepository;
    private final PasteTextTagRepository pasteTextTagRepository;

    @Override
    public String createPasteTextURL(CreatePasteTextDTO request, String author) {
        log.info("Creating text with details => {}", request);

        Employee employee = getAuthor(author);

        PasteText pasteText = createPasteText(request, employee);

        return getPasteTextUri(pasteText.getId());
    }

    @Override
    public PasteText createPasteText(CreatePasteTextDTO request, Employee employee) {

        List<PasteTextTag> pasteTextTags = getTags(request.getTags());

        LocalDateTime now = LocalDateTime.now();
        //calculate the expiry time if expiry is chosen
        LocalDateTime expirationDate = calculateExpiryDate(now, request.getExpiration());

        PasteText pasteText = PasteText.builder()
                .text(request.getText())
                .author(employee)
                .creationDate(now)
                .exposure(request.getExposure())
                .expirationDate(expirationDate)
                .tags(pasteTextTags)
                .category(request.getCategory())
                .expiration(request.getExpiration())
                .build();

        pasteText = pasteTextRepository.save(pasteText);
        return pasteText;
    }

    @Override
    public String updatePasteTextURL(UpdatePasteTextDTO request, String author) {
        log.info("Updating text with details => {}", request);

        Employee employee = getAuthor(author);

        PasteText pasteText = updatePasteText(request, employee);

        return getPasteTextUri(pasteText.getId());
    }

    @Override
    public PasteText updatePasteText(UpdatePasteTextDTO request, Employee employee) {
        PasteText pasteText = getPasteText(request.getId());

        validateUserCanUpdate(pasteText.getAuthor(), employee.getId());

        //check if request contain tags
        List<PasteTextTag> pasteTextTags = getTags(request.getTags());

        LocalDateTime now = LocalDateTime.now();
        //calculate the expiry time if expiry is chosen
        LocalDateTime expirationDate = calculateExpiryDate(now, request.getExpiration());

        pasteText.setText(request.getText());
        pasteText.setExpirationDate(expirationDate);
        pasteText.setTags(pasteTextTags);
        pasteText.setCategory(request.getCategory());
        pasteText.setExpiration(request.getExpiration());
        pasteText.setUpdatedDate(now);

        pasteText = pasteTextRepository.save(pasteText);
        return pasteText;
    }

    @Override
    public void deletePasteText(Long id, String author) {
        log.info("Deleting text with id => {} by => {}", id, author);

        PasteText pasteText = getPasteText(id);

        Employee employee = getAuthor(author);

        validateUserCanUpdate(pasteText.getAuthor(), employee.getId());

        updateDeletedStatus(pasteText);
    }

    @Override
    public PasteTextDTO viewPasteText(Long id, String requester) {
        log.info("Viewing text with id => {} by => {}", id, requester);

        PasteText pasteText = getPasteText(id);

        //validate only creator can view private text
        String author = pasteText.getAuthor().getEmail();
        if (pasteText.getExposure().equals(ExposureEnum.PRIVATE) &&
                !requester.equals(author)) {
            throw new AuthorizationException("Only " + author + " can view this");
        }

        if (pasteText.getExpiration().equals(ExpirationEnum.Burn_after_read)) {
            updateDeletedStatus(pasteText);
        }

        return getPasteTextDTO(pasteText);
    }


    @Override
    public PasteTextDTOList getAllPasteTexts(int size, int page, String requester) {
        log.info("Getting all public texts with size => {} and page => {} by => {}", size, page, requester);

        //get pageable
        Pageable pageable = getPageable(size, page);

        //get a paginated list of all public texts
        Page<PasteText> pasteTextPageable = pasteTextRepository
                .findAllByExposureAndDeletedIsFalse(ExposureEnum.PUBLIC, pageable);

        return getPasteTextDTOList(pasteTextPageable);
    }

    @Override
    public PasteTextDTOList getMyPasteTexts(int size, int page, String requester) {
        log.info("Getting all {} texts with size => {} and page => {}", requester, size, page);

        //get pageable
        Pageable pageable = getPageable(size, page);

        //get a paginated list of all public texts
        Page<PasteText> pasteTextPageable = pasteTextRepository
                .findAllByAuthor_EmailAndDeletedIsFalse(requester, pageable);

        return getPasteTextDTOList(pasteTextPageable);
    }

    @Override
    public void updateDeletedStatus(PasteText pasteText) {
        pasteText.setDeleted(true);
        pasteText.setDeletedDate(LocalDateTime.now());

        pasteTextRepository.save(pasteText);
    }

    @Override
    public void getPasteTextsToDelete() {
        List<PasteText> pasteTexts = pasteTextRepository.getAllToBeDeleted(LocalDateTime.now());

        if (pasteTexts.isEmpty()) {
            log.info("No pending text to delete");
            return;
        }

        log.info("{} pending text to delete", pasteTexts.size());
        pasteTexts.forEach(this::updateDeletedStatus);
    }


    private PasteTextDTOList getPasteTextDTOList(Page<PasteText> pasteTextPageable) {
        PasteTextDTOList pasteTextDTOList = new PasteTextDTOList();

        List<PasteText> pasteTextList = pasteTextPageable.getContent();
        if (pasteTextPageable.getContent().size() > 0) {
            pasteTextDTOList.setHasNextRecord(pasteTextPageable.hasNext());
            pasteTextDTOList.setTotalCount((int) pasteTextPageable.getTotalElements());
        }

        List<PasteTextDTO> pasteTextDTOS = convertToPasteTextDTOList(pasteTextList);
        pasteTextDTOList.setPasteTextList(pasteTextDTOS);

        return pasteTextDTOList;
    }

    private List<PasteTextDTO> convertToPasteTextDTOList(List<PasteText> pasteTextList) {
        return pasteTextList.stream().map(this::getPasteTextDTO).toList();
    }

    private static Pageable getPageable(int size, int page) {
        Pageable pageable = Pageable.ofSize(size);
        pageable.getSort().and(Sort.by("id").descending());

        if (page != 0) {
            pageable = pageable.next();
        }
        return pageable;
    }

    private PasteTextDTO getPasteTextDTO(PasteText pasteText) {
        List<String> tags = null;
        if (pasteText.getTags() != null && !pasteText.getTags().isEmpty()) {
            tags = pasteText.getTags().stream().map(PasteTextTag::getTag).toList();
        }

        return PasteTextDTO.builder()
                .id(pasteText.getId())
                .text(pasteText.getText())
                .author(pasteText.getAuthor().getEmail())
                .category(pasteText.getCategory().description)
                .creationDate(pasteText.getCreationDate())
                .updatedDate(pasteText.getUpdatedDate())
                .expirationDate(pasteText.getExpirationDate())
                .exposure(pasteText.getExposure().description)
                .expiration(pasteText.getExpiration().description)
                .tags(tags)
                .build();
    }


    //get paste that has not been deleted
    private PasteText getPasteText(Long id) {
        return pasteTextRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException("Text with Id does not exist"));
    }

    private static void validateUserCanUpdate(Employee author, Long requesterId) {
        Long authorId = author.getId();
        if (!requesterId.equals(authorId)) {
            throw new AuthorizationException("Only " + author.getEmail() + " can update this");
        }
    }

    //get url from request, get last forward slas, replace it value with the text id
    private String getPasteTextUri(Long id) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        return uri + "/" + id;
    }

    private List<PasteTextTag> getTags(List<String> tags) {
        //check if request contain tags
        List<PasteTextTag> pasteTextTags = null;
        if (tags != null && !tags.isEmpty()) {
            pasteTextTags = saveAndGetTagIds(tags);
        }
        return pasteTextTags;
    }

    private Employee getAuthor(String email) {
        return employeeService.getEmployee(email);
    }

    private static LocalDateTime calculateExpiryDate(LocalDateTime now, ExpirationEnum expirationEnum) {

        switch (expirationEnum) {
            case Minutes_10 -> {
                return now.plusMinutes(10);
            }
            case Hour_1 -> {
                return now.plusHours(1);
            }
            case Day_1 -> {
                return now.plusDays(1);
            }
            case Week_1 -> {
                return now.plusWeeks(1);
            }
            case Week_2 -> {
                return now.plusWeeks(2);
            }
            case Month_1 -> {
                return now.plusMonths(1);
            }
            default -> {
                return null;
            }
        }
    }

    private List<PasteTextTag> saveAndGetTagIds(List<String> tags) {
        HashSet<String> textTags = new HashSet<>(tags);
        return textTags.stream().map(tag -> pasteTextTagRepository.findByTag(tag)
                .orElseGet(() -> createPasteTextTag(tag))).filter(Objects::nonNull).toList();
    }

    private PasteTextTag createPasteTextTag(String tag) {
        try {
            PasteTextTag textTag = PasteTextTag.builder().tag(tag).build();
            return pasteTextTagRepository.save(textTag);
        } catch (Exception e) {
            //if it throws a unique constraint exception
            log.info(e.getMessage());
            return null;
        }
    }
}
