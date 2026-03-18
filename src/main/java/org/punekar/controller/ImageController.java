//package org.punekar.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.punekar.entity.IssueImage;
//import org.punekar.entity.Vote;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/images")
//@RequiredArgsConstructor
//public class ImageController {
//
//    private final ImageService imageService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<IssueImage> uploadImage(@RequestParam("file") MultipartFile file) {
//        // upload image
//        return null;
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<IssueImage> getImage(@PathVariable Long id) {
//        // fetch image metadata
//        return null;
//    }
//}