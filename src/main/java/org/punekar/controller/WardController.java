package org.punekar.controller;

import org.punekar.dto.IssueDTO;
import org.punekar.dto.UserDTO;
import org.punekar.entity.Issue;
import org.punekar.entity.User;
import org.punekar.entity.Ward;
import org.punekar.service.WardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/wards")
public class WardController {

    private final WardService wardService;

    public WardController(WardService wardService) {
        this.wardService = wardService;
    }

    @GetMapping
    public List<Ward> getAllWards() {
        return wardService.getAllWards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ward> getWardById(@PathVariable Long id) {
        return ResponseEntity.ok(wardService.getWardById(id));
    }

    @PostMapping
    public Ward createWard(@RequestBody Ward wardDTO) {
        return wardService.createWard(wardDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ward> updateWard(@PathVariable Long id, @RequestBody Ward ward){
        try {
            return ResponseEntity.ok(wardService.updateWard(id, ward));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteWard(@PathVariable Long id) {
        wardService.deleteWard(id);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<UserDTO>> getUsersByWard(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(wardService.getUsersByWardId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/issues")
    public ResponseEntity<List<IssueDTO>> getIssuesByWard(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(wardService.getIssuesByWardId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}