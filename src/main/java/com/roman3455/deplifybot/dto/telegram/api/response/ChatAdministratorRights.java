package com.roman3455.deplifybot.dto.telegram.api.response;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * DTO representing the rights and permissions of a chat administrator in a Telegram chat.
 *
 * <p>The {@code ChatAdministratorRights} class defines a set of permissions that can be granted to a user
 * who has administrative privileges in a Telegram chat. Each field represents a specific right that the
 * administrator may or may not possess. Some of these fields are boolean flags, while others are nullable
 * {@link Boolean} to account for optional permissions.</p>
 *
 * @param isAnonymous             required. Indicates whether the administrator can perform actions anonymously in
 *                                the chat.
 * @param canManageChat           required. Indicates whether the administrator can manage the chat settings (e.g.,
 *                                rename or change description).
 * @param canDeleteMessages       required. Indicates whether the administrator can delete messages in the chat.
 * @param canManageVideoChats     required. Indicates whether the administrator can manage video chats in the chat.
 * @param canRestrictMembers      required. Indicates whether the administrator can restrict members in the chat.
 * @param canPromoteMembers       required. Indicates whether the administrator can promote or demote members in
 *                                the chat.
 * @param canChangeInfo           required. Indicates whether the administrator can change the chat information (e.g.,
 *                                description, title).
 * @param canInviteUsers          required. Indicates whether the administrator can invite new users to the chat.
 * @param canPostStories          required. Indicates whether the administrator can post stories in the chat.
 * @param canEditStories          required. Indicates whether the administrator can edit stories in the chat.
 * @param canDeleteStories        required. Indicates whether the administrator can delete stories in the chat.
 * @param canPostMessages         optional. Indicates whether the administrator can post messages in the chat.
 * @param canEditMessages         optional. Indicates whether the administrator can edit messages in the chat.
 * @param canPinMessages          optional. Indicates whether the administrator can pin messages in the chat.
 * @param canManageTopics         optional. Indicates whether the administrator can manage topics in the chat.
 * @param canManageDirectMessages optional. Indicates whether the administrator can manage direct messages in the chat.
 * @see <a href="https://core.telegram.org/bots/api#chatadministratorrights">Telegram API — ChatAdministratorRights</a>
 */
public record ChatAdministratorRights(

        @NotNull
        Boolean isAnonymous,

        @NotNull
        Boolean canManageChat,

        @NotNull
        Boolean canDeleteMessages,

        @NotNull
        Boolean canManageVideoChats,

        @NotNull
        Boolean canRestrictMembers,

        @NotNull
        Boolean canPromoteMembers,

        @NotNull
        Boolean canChangeInfo,

        @NotNull
        Boolean canInviteUsers,

        @NotNull
        Boolean canPostStories,

        @NotNull
        Boolean canEditStories,

        @NotNull
        Boolean canDeleteStories,

        @Nullable
        Boolean canPostMessages,

        @Nullable
        Boolean canEditMessages,

        @Nullable
        Boolean canPinMessages,

        @Nullable
        Boolean canManageTopics,

        @Nullable
        Boolean canManageDirectMessages

) {
}
