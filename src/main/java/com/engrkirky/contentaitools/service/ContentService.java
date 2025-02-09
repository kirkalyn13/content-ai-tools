package com.engrkirky.contentaitools.service;

import com.engrkirky.contentaitools.dto.ContentParams;
import com.engrkirky.contentaitools.dto.GeneratedContent;

public interface ContentService {
    GeneratedContent generateContent(ContentParams contentParams);
    String generateFormattedContent(ContentParams contentParams);
}
