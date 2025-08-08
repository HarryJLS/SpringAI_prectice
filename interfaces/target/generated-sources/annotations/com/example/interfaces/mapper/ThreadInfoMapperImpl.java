package com.example.interfaces.mapper;

import com.example.domain.model.ThreadInfo;
import com.example.interfaces.dto.ThreadInfoDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-08T18:01:23+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
public class ThreadInfoMapperImpl implements ThreadInfoMapper {

    @Override
    public ThreadInfoDto toDto(ThreadInfo threadInfo) {
        if ( threadInfo == null ) {
            return null;
        }

        ThreadInfoDto threadInfoDto = new ThreadInfoDto();

        threadInfoDto.setThreadIndex( threadInfo.getThreadIndex() );
        threadInfoDto.setThreadName( threadInfo.getThreadName() );
        threadInfoDto.setExecutionTime( threadInfo.getExecutionTime() );

        return threadInfoDto;
    }

    @Override
    public ThreadInfo toDomain(ThreadInfoDto threadInfoDto) {
        if ( threadInfoDto == null ) {
            return null;
        }

        int threadIndex = 0;
        String threadName = null;
        String executionTime = null;

        if ( threadInfoDto.getThreadIndex() != null ) {
            threadIndex = threadInfoDto.getThreadIndex();
        }
        threadName = threadInfoDto.getThreadName();
        executionTime = threadInfoDto.getExecutionTime();

        ThreadInfo threadInfo = new ThreadInfo( threadIndex, threadName, executionTime );

        return threadInfo;
    }

    @Override
    public List<ThreadInfoDto> toDtoList(List<ThreadInfo> threadInfos) {
        if ( threadInfos == null ) {
            return null;
        }

        List<ThreadInfoDto> list = new ArrayList<ThreadInfoDto>( threadInfos.size() );
        for ( ThreadInfo threadInfo : threadInfos ) {
            list.add( toDto( threadInfo ) );
        }

        return list;
    }

    @Override
    public List<ThreadInfo> toDomainList(List<ThreadInfoDto> threadInfoDtos) {
        if ( threadInfoDtos == null ) {
            return null;
        }

        List<ThreadInfo> list = new ArrayList<ThreadInfo>( threadInfoDtos.size() );
        for ( ThreadInfoDto threadInfoDto : threadInfoDtos ) {
            list.add( toDomain( threadInfoDto ) );
        }

        return list;
    }
}
