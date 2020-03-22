package jbourne.demo.friendsternet.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FriendResultDto {
    private Boolean success;
    private List<String> friends;
    private List<String> recipients;
    private List<String> messages;
    private Integer count;
}
