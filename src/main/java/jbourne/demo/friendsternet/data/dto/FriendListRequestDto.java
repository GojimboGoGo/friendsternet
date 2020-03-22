package jbourne.demo.friendsternet.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data
public class FriendListRequestDto {
    private @NonNull String email;
}
