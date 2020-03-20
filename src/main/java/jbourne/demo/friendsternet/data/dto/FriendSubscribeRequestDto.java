package jbourne.demo.friendsternet.data.dto;

import lombok.*;

@NoArgsConstructor
@Data
public class FriendSubscribeRequestDto {
    private @NonNull String requestor;
    private @NonNull String target;
}
