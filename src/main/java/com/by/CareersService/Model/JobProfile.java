package com.by.CareersService.Model;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(value = "job")
public class JobProfile {

    @PrimaryKeyColumn(value = "job_id", type = PrimaryKeyType.PARTITIONED)
    @NonNull
    private String job_id;

    @Column(value = "job_name")
    @NonNull
    private String job_name;

    @PrimaryKeyColumn(value = "java_exp")
    @NonNull
    private Double java_exp;

    @PrimaryKeyColumn(value = "spring_exp")
    @NonNull
    private Double spring_exp;

    @Transient
    private String status;
}
