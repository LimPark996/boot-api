package org.example.bootapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.bootapi.model.entity.Diary;
import org.example.bootapi.model.form.DiaryForm;
import org.example.bootapi.service.DiaryService;
import org.example.bootapi.service.StorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final StorageService storageService;
    private final DiaryService diaryService;

    @GetMapping
    public String list(Model model) throws Exception {
        model.addAttribute("title", "일기 목록");
        model.addAttribute("message", "일기를 작성해보세요!");
//        model.addAttribute("form", DiaryForm.empty());
        model.addAttribute("list", diaryService.getAllDiaryList());
        return "diary/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) throws Exception {
        model.addAttribute("form", DiaryForm.empty());
        return "diary/form";
    }

    @PostMapping("/new")
    public String post(DiaryForm form, RedirectAttributes redirectAttributes) throws Exception {
        Diary diary = new Diary();
        diary.setTitle(form.title());
        diary.setContent(form.content());
        // 여기서 한 번 더 검사...
        // TIL : Validation
        // https? : 인증서. SSL/TLS -> 해싱.
        if (!form.file().isEmpty()) {
            String imageName = storageService.upload(form.file());
            redirectAttributes.addFlashAttribute("image", imageName);
            diary.setFilename(imageName); // 이거 빼먹지 마세요!
        }
        diaryService.createDiary(diary);
        return "redirect:/diary";
    }
}
