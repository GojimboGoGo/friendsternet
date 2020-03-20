package jbourne.demo.friendsternet.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.util.List;

@Builder
@Accessors(fluent = true)
@Getter
public class FriendResultDto {
    private final @NonNull Boolean success;
    private List<String> friends;
    private List<String> recipients;
    private List<String> messages;
    private Integer count;
}
