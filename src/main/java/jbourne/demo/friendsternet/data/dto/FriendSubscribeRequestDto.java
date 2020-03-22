package jbourne.demo.friendsternet.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data
public class FriendSubscribeRequestDto {
    private @NonNull String requestor;
    private @NonNull String target;
}
