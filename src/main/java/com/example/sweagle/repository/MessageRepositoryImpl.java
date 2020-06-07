package com.example.sweagle.repository;

import com.example.sweagle.model.Message;
import com.example.sweagle.resource.PredictDayDTO;
import com.example.sweagle.resource.PredictWeekDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class MessageRepositoryImpl implements MessageRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MessageRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Long aggregateMessageCountPerDay() {
        DayOfWeek today = (LocalDate.now()).getDayOfWeek();
        Aggregation aggregation = newAggregation(
                project("id").andExpression("isoDayOfWeek(date)").as("day_of_week"),
                match(Criteria.where("day_of_week").is(today.getValue())),
                group("day_of_week").count().as("msgCount"),
                project("msgCount").and("dayOfWeek").previousOperation()
        );

        AggregationResults<PredictDayDTO> results = mongoTemplate.aggregate(aggregation,
                Message.class, PredictDayDTO.class);
        List<PredictDayDTO> predictDayDtos = results.getMappedResults();
        if (predictDayDtos.size() == 1) {
            return (predictDayDtos.get(0)).getMsgCount();
        }
        return 0L;
    }

    @Override
    public Long aggregateMessageCountPerWeek() {
        int currentWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1;

        Aggregation aggregation = newAggregation(
                project("id").andExpression("isoWeek(date)").as("week_of_year"),
                match(Criteria.where("week_of_year").is(currentWeek)),
                group("week_of_year").count().as("msgCount"),
                project("msgCount").and("weekOfYear").previousOperation()
        );

        AggregationResults<PredictWeekDTO> results = mongoTemplate.aggregate(aggregation,
                Message.class, PredictWeekDTO.class);
        List<PredictWeekDTO> predictDayDtos = results.getMappedResults();
        if (predictDayDtos.size() == 1) {
            return (predictDayDtos.get(0)).getMsgCount();
        }
        return 0L;

    }

    @Override
    public Long getTodayMessageCount() {
        LocalDateTime startDateTime = LocalDateTime.now();
        startDateTime = startDateTime.with(LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.now();
        endDateTime = endDateTime.with(LocalTime.MAX);

        Query query = new Query(Criteria.where("date").gte(startDateTime).lte(endDateTime));
        List<Message> messages = mongoTemplate.find(query, Message.class);

        return (long) messages.size();
    }

    @Override
    public Long getWeekMessageCount() {
        int weekOfYear = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

        ProjectionOperation projectStage = project()
                .andExpression("isoWeek(date)").as("weekOfYear");

        MatchOperation matchStage = Aggregation.match(new Criteria("weekOfYear").is(weekOfYear));

        Aggregation aggregation
                = Aggregation.newAggregation(projectStage, matchStage);

        AggregationResults<PredictWeekDTO> output
                = mongoTemplate.aggregate(aggregation, Message.class, PredictWeekDTO.class);

        return (long)output.getMappedResults().size();
    }
}