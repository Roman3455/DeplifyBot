package com.roman3455.deplifybot.dto.telegram.api;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import com.roman3455.deplifybot.dto.telegram.api.enums.ChatMemberStatusType;
import com.roman3455.deplifybot.dto.telegram.api.enums.ChatType;
import com.roman3455.deplifybot.dto.telegram.api.enums.ParseModeType;
import com.roman3455.deplifybot.dto.telegram.api.enums.UpdateType;
import com.roman3455.deplifybot.dto.telegram.api.request.BotCommandScope;
import com.roman3455.deplifybot.dto.telegram.api.request.BotDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.BotShortDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.MyCommand;
import com.roman3455.deplifybot.dto.telegram.api.request.SendMessageRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetMyCommandsRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetWebhookRequest;
import com.roman3455.deplifybot.dto.telegram.api.response.CallbackQuery;
import com.roman3455.deplifybot.dto.telegram.api.response.Chat;
import com.roman3455.deplifybot.dto.telegram.api.response.ChatAdministratorRights;
import com.roman3455.deplifybot.dto.telegram.api.response.ChatMember;
import com.roman3455.deplifybot.dto.telegram.api.response.ChatMemberUpdated;
import com.roman3455.deplifybot.dto.telegram.api.response.ChatShared;
import com.roman3455.deplifybot.dto.telegram.api.response.Message;
import com.roman3455.deplifybot.dto.telegram.api.response.ResponseBody;
import com.roman3455.deplifybot.dto.telegram.api.response.Update;
import com.roman3455.deplifybot.dto.telegram.api.response.User;
import com.roman3455.deplifybot.dto.telegram.api.ui.ForceReply;
import com.roman3455.deplifybot.dto.telegram.api.ui.InlineKeyboardMarkup;
import com.roman3455.deplifybot.dto.telegram.api.ui.ReplyKeyboardMarkup;
import com.roman3455.deplifybot.dto.telegram.api.ui.ReplyKeyboardRemove;
import com.roman3455.deplifybot.dto.telegram.api.ui.ReplyMarkup;
import com.roman3455.deplifybot.dto.telegram.api.ui.button.CopyTextButton;
import com.roman3455.deplifybot.dto.telegram.api.ui.button.InlineKeyboardButton;
import com.roman3455.deplifybot.dto.telegram.api.ui.button.KeyboardButton;
import com.roman3455.deplifybot.dto.telegram.api.ui.button.KeyboardButtonRequestChat;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

public final class TelegramApiDtoBuilder {

    private TelegramApiDtoBuilder() {
    }

    private static final long CHAT_ID = -13435667;
    private static final long LONG_ID = 156789L;
    private static final Instant DATE_TIME = Instant.ofEpochSecond(1710248593);
    private static final String COMMAND_NAME = "command";
    private static final String FIRST_NAME = "John";
    private static final String INVALID_URL = "htps://ok";
    private static final String LANGUAGE_CODE_INVALID = "xx";
    private static final String LANGUAGE_CODE_TOO_SHORT = "e";
    private static final String LANGUAGE_CODE_TOO_LONG = "eng";
    private static final String TEXT = "text";
    private static final String TEXT_BLANK = "   ";
    private static final String TEXT_TITLE = "title";
    private static final String TEXT_DESCRIPTION = "description";
    private static final String USERNAME = "@username";
    private static final String VALID_LANGUAGE_CODE = "en";
    private static final String VALID_URL = "https://ok";

    public static BotCommandScope validBotCommandScopeFullPayload() {
        return new BotCommandScope(BotCommandScopeType.CHAT, LONG_ID);
    }

    public static BotCommandScope validBotCommandScopeRequiredPayload() {
        return new BotCommandScope(BotCommandScopeType.ALL_GROUP_CHATS, null);
    }

    public static BotCommandScope invalidBotCommandScopeWithNullType() {
        return new BotCommandScope(null, LONG_ID);
    }

    public static BotCommandScope invalidBotCommandScopeWithNullTypeAndChatId() {
        return new BotCommandScope(null, null);
    }

    public static BotCommandScope invalidBotCommandScopeWithChatIdNotPresentAndTypeIsChat() {
        return new BotCommandScope(BotCommandScopeType.CHAT, null);
    }

    public static BotCommandScope invalidBotCommandScopeWithChatIdPresentAndTypeNotChat() {
        return new BotCommandScope(BotCommandScopeType.DEFAULT, LONG_ID);
    }

    public static BotDescriptionRequest validBotDescriptionRequestFullPayload() {
        return new BotDescriptionRequest(TEXT_DESCRIPTION, VALID_LANGUAGE_CODE);
    }

    public static BotDescriptionRequest validBotDescriptionRequestDescriptionPayload() {
        return new BotDescriptionRequest(TEXT_DESCRIPTION, null);
    }

    public static BotDescriptionRequest validBotDescriptionRequestLanguageCodePayload() {
        return new BotDescriptionRequest(null, VALID_LANGUAGE_CODE);
    }

    public static BotDescriptionRequest invalidBotDescriptionRequestWithDescriptionAboveMax() {
        final int descriptionBoundLength = 512;
        return new BotDescriptionRequest("a".repeat(descriptionBoundLength + 1), null);
    }

    public static BotDescriptionRequest invalidBotDescriptionRequestWithUnknownLanguageCode() {
        return new BotDescriptionRequest(null, LANGUAGE_CODE_INVALID);
    }

    public static BotDescriptionRequest invalidBotDescriptionRequestWithLanguageCodeMoreThenTwoChars() {
        return new BotDescriptionRequest(null, LANGUAGE_CODE_TOO_LONG);
    }

    public static BotDescriptionRequest invalidBotDescriptionRequestWithLanguageCodeLessThenTwoChars() {
        return new BotDescriptionRequest(null, LANGUAGE_CODE_TOO_SHORT);
    }

    public static BotDescriptionRequest invalidBotDescriptionRequestWithNullDescriptionAndLanguageCode() {
        return new BotDescriptionRequest(null, null);
    }

    public static BotShortDescriptionRequest validBotShortDescriptionRequestFullPayload() {
        return new BotShortDescriptionRequest(TEXT_DESCRIPTION, VALID_LANGUAGE_CODE);
    }

    public static BotShortDescriptionRequest validBotShortDescriptionRequestDescriptionPayload() {
        return new BotShortDescriptionRequest(TEXT_DESCRIPTION, null);
    }

    public static BotShortDescriptionRequest validBotShortDescriptionRequestLanguageCodePayload() {
        return new BotShortDescriptionRequest(null, VALID_LANGUAGE_CODE);
    }

    public static BotShortDescriptionRequest invalidBotShortDescriptionRequestWithDescriptionAboveMax() {
        final int descriptionBoundLength = 120;
        return new BotShortDescriptionRequest("b".repeat(descriptionBoundLength + 1), null);
    }

    public static BotShortDescriptionRequest invalidBotShortDescriptionRequestWithUnknownLanguageCode() {
        return new BotShortDescriptionRequest(null, LANGUAGE_CODE_INVALID);
    }

    public static BotShortDescriptionRequest invalidBotShortDescriptionRequestWithLanguageCodeMoreThenTwoChars() {
        return new BotShortDescriptionRequest(null, LANGUAGE_CODE_TOO_LONG);
    }

    public static BotShortDescriptionRequest invalidBotShortDescriptionRequestWithLanguageCodeLessThenTwoChars() {
        return new BotShortDescriptionRequest(null, LANGUAGE_CODE_TOO_SHORT);
    }

    public static BotShortDescriptionRequest invalidBotShortDescriptionRequestWithNullDescriptionAndLanguageCode() {
        return new BotShortDescriptionRequest(null, null);
    }

    public static CallbackQuery validCallbackQueryFullPayload() {
        return new CallbackQuery(
                String.valueOf(LONG_ID),
                validUserRequiredPayload(),
                validMessageRequiredPayload(),
                TEXT
        );
    }

    public static CallbackQuery validCallbackQueryRequiredPayload() {
        return new CallbackQuery(
                String.valueOf(LONG_ID),
                validUserRequiredPayload(),
                null,
                null
        );
    }

    public static CallbackQuery invalidCallbackQueryWithNullId() {
        return new CallbackQuery(
                null,
                validUserRequiredPayload(),
                null,
                null
        );
    }

    public static CallbackQuery invalidCallbackQueryWithNullUser() {
        return new CallbackQuery(
                String.valueOf(LONG_ID),
                null,
                null,
                null
        );
    }

    public static CallbackQuery invalidCallbackQueryWithInvalidUser() {
        return new CallbackQuery(
                String.valueOf(LONG_ID),
                invalidUserWithNullId(),
                null,
                null
        );
    }

    public static CallbackQuery invalidCallbackQueryWithInvalidMessage() {
        return new CallbackQuery(
                String.valueOf(LONG_ID),
                validUserRequiredPayload(),
                invalidMessageWithNullMessageId(),
                null
        );
    }

    public static Chat validChatFullPayload() {
        return new Chat(LONG_ID, ChatType.PRIVATE, TEXT_TITLE, USERNAME, FIRST_NAME, true);
    }

    public static Chat validChatRequiredPayload() {
        return new Chat(LONG_ID, ChatType.SUPERGROUP, null, null, null, null);
    }

    public static Chat invalidChatWithNullId() {
        return new Chat(null, ChatType.PRIVATE, null, null, null, null);
    }

    public static Chat invalidChatWithNullType() {
        return new Chat(LONG_ID, null, null, null, null, null);
    }

    public static ChatAdministratorRights validChatAdministratorRightsFullPayload() {
        return new ChatAdministratorRights(
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                true,
                true,
                true,
                false,
                false
        );
    }

    public static ChatAdministratorRights validChatAdministratorRightsRequiredPayload() {
        return new ChatAdministratorRights(
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatAdministratorRights invalidChatAdministratorRightsWithNullIsAnonymous() {
        return new ChatAdministratorRights(
                null,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatAdministratorRights invalidChatAdministratorRightsWithNullCanManageChat() {
        return new ChatAdministratorRights(
                true,
                null,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatAdministratorRights invalidChatAdministratorRightsWithNullCanDeleteMessages() {
        return new ChatAdministratorRights(
                true,
                true,
                null,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatAdministratorRights invalidChatAdministratorRightsWithNullManageVideoChats() {
        return new ChatAdministratorRights(
                true,
                true,
                true,
                null,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatAdministratorRights invalidChatAdministratorRightsWithNullCanRestrictMembers() {
        return new ChatAdministratorRights(
                true,
                true,
                true,
                false,
                null,
                false,
                false,
                false,
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatAdministratorRights invalidChatAdministratorRightsWithNullCanPromoteMembers() {
        return new ChatAdministratorRights(
                true,
                true,
                true,
                false,
                false,
                null,
                false,
                false,
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatAdministratorRights invalidChatAdministratorRightsWithNullCanChangeInfo() {
        return new ChatAdministratorRights(
                true,
                true,
                true,
                false,
                false,
                false,
                null,
                false,
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatAdministratorRights invalidChatAdministratorRightsWithNullCanInviteUsers() {
        return new ChatAdministratorRights(
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                null,
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatAdministratorRights invalidChatAdministratorRightsWithNullCanPostStories() {
        return new ChatAdministratorRights(
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                null,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatAdministratorRights invalidChatAdministratorRightsWithNullCanEditStories() {
        return new ChatAdministratorRights(
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                null,
                true,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatAdministratorRights invalidChatAdministratorRightsWithNullCanDeleteStories() {
        return new ChatAdministratorRights(
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ChatMember validChatMemberFullPayload() {
        return new ChatMember(ChatMemberStatusType.ADMINISTRATOR, validUserRequiredPayload());
    }

    public static ChatMember invalidChatMemberWithNullStatus() {
        return new ChatMember(null, validUserRequiredPayload());
    }

    public static ChatMember invalidChatMemberWithNullUser() {
        return new ChatMember(ChatMemberStatusType.MEMBER, null);
    }

    public static ChatMember invalidChatMemberWithInvalidUser() {
        return new ChatMember(ChatMemberStatusType.MEMBER, invalidUserWithNullId());
    }

    public static ChatMemberUpdated validChatMemberUpdatedFullPayload() {
        return new ChatMemberUpdated(
                validChatRequiredPayload(),
                validUserRequiredPayload(),
                DATE_TIME,
                validChatMemberFullPayload(),
                validChatMemberFullPayload()
        );
    }

    public static ChatMemberUpdated invalidChatMemberUpdatedWithNullChat() {
        return new ChatMemberUpdated(
                null,
                validUserRequiredPayload(),
                DATE_TIME,
                validChatMemberFullPayload(),
                validChatMemberFullPayload()
        );
    }

    public static ChatMemberUpdated invalidChatMemberUpdatedWithInvalidChat() {
        return new ChatMemberUpdated(
                invalidChatWithNullId(),
                validUserRequiredPayload(),
                DATE_TIME,
                validChatMemberFullPayload(),
                validChatMemberFullPayload()
        );
    }

    public static ChatMemberUpdated invalidChatMemberUpdatedWithNullFrom() {
        return new ChatMemberUpdated(
                validChatRequiredPayload(),
                null,
                DATE_TIME,
                validChatMemberFullPayload(),
                validChatMemberFullPayload()
        );
    }

    public static ChatMemberUpdated invalidChatMemberUpdatedWithInvalidFrom() {
        return new ChatMemberUpdated(
                validChatRequiredPayload(),
                invalidUserWithNullId(),
                DATE_TIME,
                validChatMemberFullPayload(),
                validChatMemberFullPayload()
        );
    }

    public static ChatMemberUpdated invalidChatMemberUpdatedWithNullDate() {
        return new ChatMemberUpdated(
                validChatRequiredPayload(),
                validUserRequiredPayload(),
                null,
                validChatMemberFullPayload(),
                validChatMemberFullPayload()
        );
    }

    public static ChatMemberUpdated invalidChatMemberUpdatedWithNullOldChatMember() {
        return new ChatMemberUpdated(
                validChatRequiredPayload(),
                validUserRequiredPayload(),
                DATE_TIME,
                null,
                validChatMemberFullPayload()
        );
    }

    public static ChatMemberUpdated invalidChatMemberUpdatedWithInvalidOldChatMember() {
        return new ChatMemberUpdated(
                validChatRequiredPayload(),
                validUserRequiredPayload(),
                DATE_TIME,
                invalidChatMemberWithNullUser(),
                validChatMemberFullPayload()
        );
    }

    public static ChatMemberUpdated invalidChatMemberUpdatedWithNullNewChatMember() {
        return new ChatMemberUpdated(
                validChatRequiredPayload(),
                validUserRequiredPayload(),
                DATE_TIME,
                validChatMemberFullPayload(),
                null
        );
    }

    public static ChatMemberUpdated invalidChatMemberUpdatedWithInvalidNewChatMember() {
        return new ChatMemberUpdated(
                validChatRequiredPayload(),
                validUserRequiredPayload(),
                DATE_TIME,
                validChatMemberFullPayload(),
                invalidChatMemberWithNullUser()
        );
    }

    public static ChatShared validChatSharedFullPayload() {
        return new ChatShared(LONG_ID, LONG_ID, TEXT_TITLE, USERNAME);
    }

    public static ChatShared validChatSharedRequiredPayload() {
        return new ChatShared(LONG_ID, LONG_ID, null, null);
    }

    public static ChatShared invalidChatSharedWithNullRequestId() {
        return new ChatShared(null, CHAT_ID, null, null);
    }

    public static ChatShared invalidChatSharedWithNullChatId() {
        return new ChatShared(LONG_ID, null, null, null);
    }

    public static CopyTextButton validCopyTextButtonFullPayload() {
        return new CopyTextButton(TEXT);
    }

    public static CopyTextButton invalidCopyTextButtonWithBlankText() {
        return new CopyTextButton(TEXT_BLANK);
    }

    public static CopyTextButton invalidCopyTextButtonWithSizeAboveMaxText() {
        final int textBoundLength = 256;
        return new CopyTextButton("f".repeat(textBoundLength + 1));
    }

    public static InlineKeyboardButton validInlineKeyboardButtonCallbackDataPayload() {
        return InlineKeyboardButton.ofCallbackData(TEXT_TITLE, COMMAND_NAME);
    }

    public static InlineKeyboardButton validInlineKeyboardButtonCopyTextPayload() {
        return InlineKeyboardButton.ofCopyText(TEXT_TITLE, TEXT);
    }

    public static InlineKeyboardButton validInlineKeyboardButtonUrlPayload() {
        return InlineKeyboardButton.ofUrl(TEXT_TITLE, VALID_URL);
    }

    public static InlineKeyboardButton invalidInlineKeyboardButtonWithBlankText() {
        return new InlineKeyboardButton(TEXT_BLANK, VALID_URL, null, null);
    }

    public static InlineKeyboardButton invalidInlineKeyboardButtonWithMismatchUrl() {
        return new InlineKeyboardButton(TEXT_BLANK, INVALID_URL, null, null);
    }

    public static InlineKeyboardButton invalidInlineKeyboardButtonWithBytesLengthBelowMinCallbackData() {
        return new InlineKeyboardButton(TEXT_TITLE, null, "", null);
    }

    public static InlineKeyboardButton invalidInlineKeyboardButtonWithBytesLengthAboveMaxCallbackData() {
        final int bytesBoundLength = 64;
        return new InlineKeyboardButton(TEXT_TITLE, null, "f".repeat(bytesBoundLength + 1), null);
    }

    public static InlineKeyboardButton invalidInlineKeyboardButtonWithInvalidCopyTextButton() {
        return new InlineKeyboardButton(TEXT_TITLE, null, null, invalidCopyTextButtonWithBlankText());
    }

    public static InlineKeyboardButton invalidInlineKeyboardButtonWithNullOptionalFields() {
        return new InlineKeyboardButton(TEXT_TITLE, null, null, null);
    }

    public static InlineKeyboardButton invalidInlineKeyboardButtonWithAllOptionalFieldsPresent() {
        return new InlineKeyboardButton(TEXT_TITLE, VALID_URL, TEXT, validCopyTextButtonFullPayload());
    }

    public static KeyboardButton validKeyboardButtonFullPayload() {
        return new KeyboardButton(TEXT_TITLE, validKeyboardButtonRequestChatRequiredPayload());
    }

    public static KeyboardButton validKeyboardButtonRequiredPayload() {
        return KeyboardButton.ofText(TEXT_TITLE);
    }

    public static KeyboardButton invalidKeyboardButtonWithBlankText() {
        return new KeyboardButton(TEXT_BLANK, null);
    }

    public static KeyboardButton invalidKeyboardButtonWithInvalidRequestChat() {
        return new KeyboardButton(TEXT_TITLE, invalidKeyboardButtonRequestChatWithNullRequestId());
    }

    public static KeyboardButtonRequestChat validKeyboardButtonRequestChatFullPayload() {
        return new KeyboardButtonRequestChat(
                LONG_ID,
                true,
                validChatAdministratorRightsRequiredPayload(),
                validChatAdministratorRightsRequiredPayload(),
                true,
                true
        );
    }

    public static KeyboardButtonRequestChat validKeyboardButtonRequestChatRequiredPayload() {
        return new KeyboardButtonRequestChat(
                LONG_ID,
                true,
                null,
                null,
                null,
                null
        );
    }

    public static KeyboardButtonRequestChat invalidKeyboardButtonRequestChatWithNullRequestId() {
        return new KeyboardButtonRequestChat(
                null,
                true,
                null,
                null,
                null,
                null
        );
    }

    public static KeyboardButtonRequestChat invalidKeyboardButtonRequestChatWithInvalidUserAdministratorRights() {
        return new KeyboardButtonRequestChat(
                LONG_ID,
                true,
                invalidChatAdministratorRightsWithNullCanChangeInfo(),
                null,
                null,
                null
        );
    }

    public static KeyboardButtonRequestChat invalidKeyboardButtonRequestChatWithInvalidBotAdministratorRights() {
        return new KeyboardButtonRequestChat(
                LONG_ID,
                true,
                null,
                invalidChatAdministratorRightsWithNullCanChangeInfo(),
                null,
                null
        );
    }

    public static Message validMessageFullPayload() {
        return new Message(
                LONG_ID,
                CHAT_ID,
                validUserRequiredPayload(),
                DATE_TIME,
                validChatRequiredPayload(),
                TEXT,
                LONG_ID,
                LONG_ID,
                validChatSharedRequiredPayload()
        );
    }

    public static Message validMessageRequiredPayload() {
        return new Message(
                LONG_ID,
                null,
                null,
                DATE_TIME,
                validChatRequiredPayload(),
                null,
                null,
                null,
                null
        );
    }

    public static Message invalidMessageWithNullMessageId() {
        return new Message(
                null,
                null,
                null,
                DATE_TIME,
                validChatRequiredPayload(),
                null,
                null,
                null,
                null
        );
    }

    public static Message invalidMessageWithInvalidUser() {
        return new Message(
                LONG_ID,
                null,
                invalidUserWithNullId(),
                DATE_TIME,
                validChatRequiredPayload(),
                null,
                null,
                null,
                null
        );
    }

    public static Message invalidMessageWithNullDate() {
        return new Message(
                LONG_ID,
                null,
                null,
                null,
                validChatRequiredPayload(),
                null,
                null,
                null,
                null
        );
    }

    public static Message invalidMessageWithNullChat() {
        return new Message(
                LONG_ID,
                null,
                null,
                DATE_TIME,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static Message invalidMessageWithInvalidChat() {
        return new Message(
                LONG_ID,
                null,
                null,
                DATE_TIME,
                invalidChatWithNullId(),
                null,
                null,
                null,
                null
        );
    }

    public static Message invalidMessageWithInvalidChatShared() {
        return new Message(
                LONG_ID,
                null,
                null,
                DATE_TIME,
                validChatRequiredPayload(),
                null,
                null,
                null,
                invalidChatSharedWithNullChatId()
        );
    }

    public static MyCommand validMyCommandFullPayload() {
        return new MyCommand(COMMAND_NAME, TEXT_DESCRIPTION);
    }

    public static MyCommand invalidMyCommandWithBlankCommand() {
        return new MyCommand("", TEXT_DESCRIPTION);
    }

    public static MyCommand invalidMyCommandWithBlankDescription() {
        return new MyCommand(COMMAND_NAME, "");
    }

    public static MyCommand invalidMyCommandWithSizeAboveMaxCommand() {
        final int commandBoundChars = 32;
        return new MyCommand("c".repeat(commandBoundChars + 1), TEXT_DESCRIPTION);
    }

    public static MyCommand invalidMyCommandWithSizeAboveMaxDescription() {
        final int descriptionBoundChars = 256;
        return new MyCommand(COMMAND_NAME, "d".repeat(descriptionBoundChars + 1));
    }

    public static MyCommand invalidMyCommandWithMismatchCommandPattern() {
        return new MyCommand("/" + COMMAND_NAME, TEXT_DESCRIPTION);
    }

    public static ReplyMarkup validForceReplyFullPayload() {
        return ForceReply.withPlaceholder(TEXT_DESCRIPTION);
    }

    public static ReplyMarkup validForceReplyRequiredPayload() {
        return ForceReply.empty();
    }

    public static ReplyMarkup invalidForceReplyWithFalseForceReply() {
        return new ForceReply(false, null);
    }

    public static ReplyMarkup invalidForceReplyWithSizeBelowMinPlaceholder() {
        return new ForceReply(true, "");
    }

    public static ReplyMarkup invalidForceReplyWithSizeAboveMaxPlaceholder() {
        final int placeholderBoundChars = 64;
        return new ForceReply(true, "g".repeat(placeholderBoundChars + 1));
    }

    public static ReplyMarkup validInlineKeyboardMarkupFullPayload() {
        return InlineKeyboardMarkup.ofRows(
                List.of(validInlineKeyboardButtonUrlPayload()),
                List.of(validInlineKeyboardButtonCallbackDataPayload())
        );
    }

    public static ReplyMarkup invalidInlineKeyboardMarkupWithEmptyInlineKeyboard() {
        return new InlineKeyboardMarkup(List.of());
    }

    public static ReplyMarkup invalidInlineKeyboardMarkupWithNestedEmptyInlineKeyboard() {
        return new InlineKeyboardMarkup(List.of(List.of()));
    }

    public static ReplyMarkup validInlineKeyboardMarkupWithInvalidInlineKeyboardButton() {
        return new InlineKeyboardMarkup(List.of(List.of(invalidInlineKeyboardButtonWithBlankText())));
    }

    public static ReplyMarkup validReplyKeyboardMarkupFullPayload() {
        return new ReplyKeyboardMarkup(
                List.of(List.of(validKeyboardButtonRequiredPayload())),
                true,
                true,
                true,
                TEXT_DESCRIPTION
        );
    }

    public static ReplyMarkup validReplyKeyboardMarkupRequiredPayload() {
        return ReplyKeyboardMarkup.ofRows(
                null,
                null,
                null,
                null,
                List.of(validKeyboardButtonRequiredPayload())
        );
    }

    public static ReplyMarkup invalidReplyKeyboardMarkupWithEmptyKeyboard() {
        return new ReplyKeyboardMarkup(
                List.of(),
                null,
                null,
                null,
                null
        );
    }

    public static ReplyMarkup invalidReplyKeyboardMarkupWithNestedEmptyKeyboard() {
        return new ReplyKeyboardMarkup(
                List.of(List.of()),
                null,
                null,
                null,
                null
        );
    }

    public static ReplyMarkup invalidReplyKeyboardMarkupWithInvalidKeyboard() {
        return new ReplyKeyboardMarkup(
                List.of(List.of(invalidKeyboardButtonWithBlankText())),
                null,
                null,
                null,
                null
        );
    }

    public static ReplyMarkup invalidReplyKeyboardMarkupWithSizeBelowMinPlaceholder() {
        return new ReplyKeyboardMarkup(
                List.of(List.of(validKeyboardButtonRequiredPayload())),
                null,
                null,
                null,
                ""
        );
    }

    public static ReplyMarkup invalidReplyKeyboardMarkupWithSizeAboveMaxPlaceholder() {
        final int placeholderBoundChars = 64;
        return new ReplyKeyboardMarkup(
                List.of(List.of(validKeyboardButtonRequiredPayload())),
                null,
                null,
                null,
                "h".repeat(placeholderBoundChars + 1)
        );
    }

    public static ReplyMarkup validReplyKeyboardRemoveFullPayload() {
        return new ReplyKeyboardRemove(true);
    }

    public static ReplyMarkup invalidReplyKeyboardRemoveWithFalseValue() {
        return new ReplyKeyboardRemove(false);
    }

    public static ResponseBody<?> validResponseBodySuccessPayload() {
        return new ResponseBody<>(
                true,
                true,
                null,
                null,
                null
        );
    }

    public static ResponseBody<?> validResponseBodyTooManyRequestsPayload() {
        final int tooManyRequests = 429;
        return new ResponseBody<>(
                false,
                null,
                tooManyRequests,
                "Too Many Requests: retry after 1",
                new ResponseBody.ResponseParameters(null, 1)
        );
    }

    public static ResponseBody<?> validResponseBodyBadRequestPayload() {
        final int badRequest = 400;
        return new ResponseBody<>(
                false,
                null,
                badRequest,
                "Bad Request: group chat was upgraded to a supergroup chat",
                new ResponseBody.ResponseParameters(CHAT_ID, null)
        );
    }

    public static SendMessageRequest validSendMessageRequestFullPayload() {
        return new SendMessageRequest(
                LONG_ID,
                CHAT_ID,
                TEXT,
                ParseModeType.HTML,
                true,
                true,
                validReplyKeyboardRemoveFullPayload()
        );
    }

    public static SendMessageRequest validSendMessageRequestPayloadWithoutReplyMarkup() {
        return new SendMessageRequest(
                LONG_ID,
                CHAT_ID,
                TEXT,
                ParseModeType.HTML,
                true,
                true,
                null
        );
    }

    public static SendMessageRequest validSendMessageRequestRequiredPayload() {
        return new SendMessageRequest(
                LONG_ID,
                null,
                TEXT,
                null,
                null,
                null,
                null
        );
    }

    public static SendMessageRequest invalidSendMessageRequestWithNullChatId() {
        return new SendMessageRequest(
                null,
                null,
                TEXT,
                null,
                null,
                null,
                null
        );
    }

    public static SendMessageRequest invalidSendMessageRequestWithBlankText() {
        return new SendMessageRequest(
                LONG_ID,
                null,
                TEXT_BLANK,
                null,
                null,
                null,
                null
        );
    }

    public static SendMessageRequest invalidSendMessageRequestWithSizeAboveMaxText() {
        final int textBoundChars = 4096;
        return new SendMessageRequest(
                LONG_ID,
                null,
                "e".repeat(textBoundChars + 1),
                null,
                null,
                null,
                null
        );
    }

    public static SendMessageRequest invalidSendMessageRequestWithInvalidReplyMarkup() {
        return new SendMessageRequest(
                LONG_ID,
                null,
                TEXT,
                null,
                null,
                null,
                invalidReplyKeyboardRemoveWithFalseValue()
        );
    }

    public static SetMyCommandsRequest validSetMyCommandsRequestFullPayload() {
        return new SetMyCommandsRequest(
                List.of(validMyCommandFullPayload()),
                validBotCommandScopeRequiredPayload(),
                VALID_LANGUAGE_CODE
        );
    }

    public static SetMyCommandsRequest validSetMyCommandsRequestRequiredPayload() {
        return new SetMyCommandsRequest(List.of(validMyCommandFullPayload()), null, null);
    }

    public static SetMyCommandsRequest invalidSetMyCommandsRequestWithEmptyCommandsList() {
        return new SetMyCommandsRequest(List.of(), null, null);
    }

    public static SetMyCommandsRequest invalidSetMyCommandsRequestWithSizeAboveMaxCommandsList() {
        final int commandsBoundValue = 100;
        var randomChars = RandomStringUtils.insecure().nextAlphabetic(2).toLowerCase();
        var invalidCommandList = IntStream.range(0, commandsBoundValue + 1)
                .mapToObj(i -> new MyCommand(randomChars, randomChars))
                .toList();
        return new SetMyCommandsRequest(invalidCommandList, null, null);
    }

    public static SetMyCommandsRequest invalidSetMyCommandsRequestWithInvalidMyCommands() {
        return new SetMyCommandsRequest(
                List.of(invalidMyCommandWithBlankCommand()), null, null
        );
    }

    public static SetMyCommandsRequest invalidSetMyCommandsRequestWithInvalidScope() {
        return new SetMyCommandsRequest(
                List.of(validMyCommandFullPayload()),
                invalidBotCommandScopeWithNullType(),
                null
        );
    }

    public static SetMyCommandsRequest invalidSetMyCommandsRequestWithUnknownLanguageCode() {
        return new SetMyCommandsRequest(
                List.of(validMyCommandFullPayload()),
                null,
                LANGUAGE_CODE_INVALID
        );
    }

    public static SetMyCommandsRequest invalidSetMyCommandsRequestWithLanguageCodeMoreThenTwoChars() {
        return new SetMyCommandsRequest(
                List.of(validMyCommandFullPayload()),
                null,
                LANGUAGE_CODE_TOO_LONG
        );
    }

    public static SetMyCommandsRequest invalidSetMyCommandsRequestWithLanguageCodeLessThenTwoChars() {
        return new SetMyCommandsRequest(
                List.of(validMyCommandFullPayload()),
                null,
                LANGUAGE_CODE_TOO_SHORT
        );
    }

    public static SetWebhookRequest validSetWebhookRequestFullPayload() {
        final int httpConnections = 80;
        return new SetWebhookRequest(
                VALID_URL,
                httpConnections,
                List.of(UpdateType.MESSAGE, UpdateType.CALLBACK_QUERY),
                true,
                "_Token-123"
        );
    }

    public static SetWebhookRequest validSetWebhookRequestRequiredPayload() {
        return new SetWebhookRequest(
                VALID_URL,
                null,
                null,
                null,
                null
        );
    }

    public static SetWebhookRequest invalidSetWebhookRequestWithEmptyUrl() {
        return new SetWebhookRequest(
                TEXT_BLANK,
                null,
                null,
                null,
                null
        );
    }

    public static SetWebhookRequest invalidSetWebhookRequestWithInvalidUrl() {
        return new SetWebhookRequest(
                INVALID_URL,
                null,
                null,
                null,
                null
        );
    }

    @SuppressWarnings("DataFlowIssue")
    public static SetWebhookRequest invalidSetWebhookRequestWithValueBelowMinConnection() {
        return new SetWebhookRequest(
                VALID_URL,
                0,
                null,
                null,
                null
        );
    }

    @SuppressWarnings("DataFlowIssue")
    public static SetWebhookRequest invalidSetWebhookRequestWithValueAboveMaxConnection() {
        final int connectionsBoundValue = 100;
        return new SetWebhookRequest(
                VALID_URL,
                connectionsBoundValue + 1,
                null,
                null,
                null
        );
    }

    public static SetWebhookRequest invalidSetWebhookRequestWithSizeBelowMinSecretToken() {
        return new SetWebhookRequest(
                VALID_URL,
                null,
                null,
                null,
                ""
        );
    }

    public static SetWebhookRequest invalidSetWebhookRequestWithSizeAboveMaxSecretToken() {
        final int tokenBoundLength = 256;
        return new SetWebhookRequest(
                VALID_URL,
                null,
                null,
                null,
                "f".repeat(tokenBoundLength + 1)
        );
    }

    public static SetWebhookRequest invalidSetWebhookRequestMismatchPatternSecretToken() {
        return new SetWebhookRequest(
                VALID_URL,
                null,
                null,
                null,
                "bad*token!"
        );
    }

    public static Update validUpdateMessagePayload() {
        return new Update(LONG_ID, validMessageRequiredPayload(), null, null);
    }

    public static Update validUpdateCallbackQueryPayload() {
        return new Update(LONG_ID, null, validCallbackQueryRequiredPayload(), null);
    }

    public static Update validUpdateMyChatMemberPayload() {
        return new Update(LONG_ID, null, null, validChatMemberUpdatedFullPayload());
    }

    public static Update invalidUpdateWithNullUpdateId() {
        return new Update(null, validMessageRequiredPayload(), null, null);
    }

    @SuppressWarnings("DataFlowIssue")
    public static Update invalidUpdateWithNegativeUpdateId() {
        return new Update(CHAT_ID, validMessageRequiredPayload(), null, null);
    }

    public static Update invalidUpdateWithInvalidMessage() {
        return new Update(LONG_ID, invalidMessageWithNullMessageId(), null, null);
    }

    public static Update invalidUpdateWithInvalidCallbackQuery() {
        return new Update(LONG_ID, null, invalidCallbackQueryWithNullId(), null);
    }

    public static Update invalidUpdateWithInvalidMyChatMember() {
        return new Update(LONG_ID, null, null, invalidChatMemberUpdatedWithNullDate());
    }

    public static Update invalidUpdateWithNullOptionalFields() {
        return new Update(LONG_ID, null, null, null);
    }

    public static Update invalidUpdateWithAllOptionalFieldsPresent() {
        return new Update(
                LONG_ID,
                validMessageRequiredPayload(),
                validCallbackQueryFullPayload(),
                validChatMemberUpdatedFullPayload()
        );
    }

    public static User validUserFullPayload() {
        return new User(LONG_ID, false, FIRST_NAME, USERNAME, VALID_LANGUAGE_CODE);
    }

    public static User validUserRequiredPayload() {
        return new User(LONG_ID, true, FIRST_NAME, null, null);
    }

    public static User invalidUserWithNullId() {
        return new User(null, false, FIRST_NAME, null, null);
    }

    public static User invalidUserWithNullFirstName() {
        return new User(LONG_ID, false, null, null, null);
    }

    public static User invalidUserWithUnknownLanguageCode() {
        return new User(LONG_ID, false, FIRST_NAME, null, LANGUAGE_CODE_INVALID);
    }

    public static User invalidUserLanguageCodeMoreThenTwoChars() {
        return new User(LONG_ID, false, FIRST_NAME, null, LANGUAGE_CODE_TOO_LONG);
    }

    public static User invalidUserWithLanguageCodeLessThenTwoChars() {
        return new User(LONG_ID, false, FIRST_NAME, null, LANGUAGE_CODE_TOO_SHORT);
    }

}
