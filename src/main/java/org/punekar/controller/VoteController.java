//package org.punekar.controller;
//
//import lombok.RequiredArgsConstructor;
//
//import org.punekar.entity.Vote;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/votes")
//@RequiredArgsConstructor
//public class VoteController {
//
//    private final VoteService voteService;
//
//    @PostMapping
//    public ResponseEntity<Vote> castVote(@RequestBody Vote voteDTO) {
//        // cast upvote/downvote
//        return null;
//    }
//
//    @GetMapping("/issue/{issueId}")
//    public ResponseEntity<Integer> getVotesForIssue(@PathVariable Long issueId) {
//        // fetch net votes for issue
//        return null;
//    }
//
//    @GetMapping("/comment/{commentId}")
//    public ResponseEntity<Integer> getVotesForComment(@PathVariable Long commentId) {
//        // fetch net votes for comment
//        return null;
//    }
//}