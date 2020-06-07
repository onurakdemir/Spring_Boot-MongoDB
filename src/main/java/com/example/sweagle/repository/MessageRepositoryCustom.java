package com.example.sweagle.repository;

/**
 * Mongodb custom query methods.
 */
public interface MessageRepositoryCustom {
    Long aggregateMessageCountPerDay();

    Long aggregateMessageCountPerWeek();

    Long getTodayMessageCount();

    Long getWeekMessageCount();
}
