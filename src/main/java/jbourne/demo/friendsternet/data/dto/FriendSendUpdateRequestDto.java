package jbourne.demo.friendsternet.data.dto;

import lombok.*;

@NoArgsConstructor
@Data
public class FriendSendUpdateRequestDto {
    private @NonNull String sender;
    private @NonNull String text;
}
