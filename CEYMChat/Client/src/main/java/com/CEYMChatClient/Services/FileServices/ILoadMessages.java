package com.CEYMChatClient.Services.FileServices;

import java.io.IOException;
import java.util.List;

public interface ILoadMessages {
     List<String> loadSavedMessages(String filename) throws IOException;
}
