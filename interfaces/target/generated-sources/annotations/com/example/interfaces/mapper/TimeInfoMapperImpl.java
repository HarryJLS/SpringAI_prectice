package com.example.interfaces.mapper;

import com.example.domain.model.TimeInfo;
import com.example.interfaces.dto.TimeInfoDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-08T18:01:23+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
public class TimeInfoMapperImpl implements TimeInfoMapper {

    @Override
    public TimeInfoDto toDto(TimeInfo timeInfo) {
        if ( timeInfo == null ) {
            return null;
        }

        TimeInfoDto timeInfoDto = new TimeInfoDto();

        timeInfoDto.setCurrentTime( timeInfo.getCurrentTime() );
        timeInfoDto.setTimestamp( timeInfo.getTimestamp() );

        return timeInfoDto;
    }

    @Override
    public TimeInfo toDomain(TimeInfoDto timeInfoDto) {
        if ( timeInfoDto == null ) {
            return null;
        }

        String currentTime = null;
        Long timestamp = null;

        currentTime = timeInfoDto.getCurrentTime();
        timestamp = timeInfoDto.getTimestamp();

        TimeInfo timeInfo = new TimeInfo( currentTime, timestamp );

        return timeInfo;
    }
}
