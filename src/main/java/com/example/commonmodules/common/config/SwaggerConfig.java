package com.example.commonmodules.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {


    @Value("${springdoc.title}")
    private String API_TITLE;

    @Value("${springdoc.version}")
    private String API_VERSION;

    private static final String API_DESCRIPTION = """
            ## Paging 설명
            
            `검색 조건이 없을 경우 전체목록 조회`
            
            ### pageable request 예시
            
            ```java
            {
              "page": 0,
              "size": 10,
              "sort": [
                "createdDate,desc"
              ]
            }
            ```
            
            - page: 선택 페이지, 0부터 시작
            - size: 페이지당 표시할 데이터 갯수 설정
            - sort: 정렬 기준 설정
                - 여러 필드로 정렬 : "createdDate", "sampleSn"
                - 단일 필드로 정렬 : "createdDate"
            
            ### pageable response 예시
            
            ```java
            "pagination": {
                "pageNumber": 1,
                "pageSize": 10,
                "numberOfElements": 10,
                "totalPages": 2,
                "totalElements": 14
              }
            ```
            
            - pageNumber: 현재 페이지 숫자
            - pageSize: 페이지당 표시할 데이터 갯수
            - numberOfElements: 현재 페이지 데이터 갯수
            - totalPages: 전체 페이지 갯수
            - totalElements: 전체 데이터 갯수
            
            ---
            
            ## Token 설명
            
            - access token: 1시간 설정
            - refresh token: 30일 설정
            """;


    /**
     * Swagger 기본 정보
     *
     * @return
     */
    private Info apiInfo() {
        return new Info()
                .title(API_TITLE) // API의 제목
                .version(API_VERSION) // API의 버전
                .description(API_DESCRIPTION); // API에 대한 설명
    }

    /**
     * Swagger OpenAPI 기본 설정 (JWT 보안 포함)
     */
    @Bean
    public OpenAPI openAPI() {

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", securityScheme)
                )
                .info(apiInfo())
                .security(Collections.singletonList(securityRequirement));
    }

    /**
     * 모든 API에 공통 응답 스펙 적용 (400/404/409/500)
     */
    @Bean
    public OpenApiCustomizer globalResponseCustomizer() {
        return openApi -> openApi.getPaths().forEach((path, pathItem) ->
                pathItem.readOperations().forEach(operation -> {
                    ApiResponses responses = operation.getResponses();

                    // 공통 응답 추가 (중복 방지)
                    addIfMissing(responses, "400", "입력값 유효성 검증 실패");
                    addIfMissing(responses, "404", "데이터 오류");
                    addIfMissing(responses, "409", "데이터 중복");
                    addIfMissing(responses, "500", "서버 내부 오류 발생");
                }));
    }

    private void addIfMissing(ApiResponses responses, String code, String description) {
        if (!responses.containsKey(code)) {
            responses.addApiResponse(code, new ApiResponse()
                    .description(description)
                    .content(new Content().addMediaType("application/json",
                            new MediaType().schema(new Schema<>().$ref("#/components/schemas/ExceptionMsg"))))); // Swagger 문서 내부 스키마 참조
        }
    }
}