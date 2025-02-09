package com.engrkirky.contentaitools.service;

import com.engrkirky.contentaitools.dto.Summary;

public interface SummaryService {
    Summary summarizeArticle(String region, String title);
}
