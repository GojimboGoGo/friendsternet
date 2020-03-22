package jbourne.demo.friendsternet.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data
public class FriendSendUpdateRequestDto {
    private @NonNull String sender;
    private @NonNull String text;
}
