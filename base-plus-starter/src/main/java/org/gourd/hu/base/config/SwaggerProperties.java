package org.gourd.hu.base.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Swagger Properties
 *
 * @author Elve.xu
 * @version 0.8
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {

  private Boolean enabled;

  private String title = "";

  private String description = "";

  private String version = "";

  private String license = "";

  private String licenseUrl = "";

  private String termsOfServiceUrl = "";

  private List<Class> ignoredParameterTypes = new ArrayList<>();

  private Contact contact = new Contact();

  private String basePackage = "";

  private String demoPackage = "";

  private List<String> basePath = new ArrayList<>();

  private List<String> excludePath = new ArrayList<>();

  private Map<String, DocketInfo> docket = new LinkedHashMap<>();

  private String host = "";

  private List<GlobalOperationParameter> globalOperationParameters;

  private UiConfig uiConfig = new UiConfig();

  private Boolean applyDefaultResponseMessages = true;

  private GlobalResponseMessage globalResponseMessage;

  @Data
  @NoArgsConstructor
  public static class GlobalOperationParameter {

    private String name;

    private String description;

    private String modelRef;

    private String parameterType;

    private String required;
  }

  @Data
  @NoArgsConstructor
  public static class DocketInfo {

    private String title = "";

    private String description = "";

    private String version = "";

    private String license = "";

    private String licenseUrl = "";

    private String termsOfServiceUrl = "";

    private Contact contact = new Contact();

    private String basePackage = "";

    private List<String> basePath = new ArrayList<>();

    private List<String> excludePath = new ArrayList<>();

    private List<GlobalOperationParameter> globalOperationParameters;

    private List<Class> ignoredParameterTypes = new ArrayList<>();
  }

  @Data
  @NoArgsConstructor
  public static class Contact {

    private String name = "";
    private String url = "";
    private String email = "";
  }

  @Data
  @NoArgsConstructor
  public static class GlobalResponseMessage {

    List<GlobalResponseMessageBody> post = new ArrayList<>();

    List<GlobalResponseMessageBody> get = new ArrayList<>();

    List<GlobalResponseMessageBody> put = new ArrayList<>();

    List<GlobalResponseMessageBody> patch = new ArrayList<>();

    List<GlobalResponseMessageBody> delete = new ArrayList<>();

    List<GlobalResponseMessageBody> head = new ArrayList<>();

    List<GlobalResponseMessageBody> options = new ArrayList<>();

    List<GlobalResponseMessageBody> trace = new ArrayList<>();
  }

  @Data
  @NoArgsConstructor
  public static class GlobalResponseMessageBody {

    private int code;
    private String message;
    private String modelRef;
  }

  @Data
  @NoArgsConstructor
  public static class UiConfig {

    private String validatorUrl;
    private String docExpansion = "none";
    private String apiSorter = "alpha";
    private String defaultModelRendering = "schema";
    private Boolean jsonEditor = false;
    private Boolean showRequestHeaders = true;
    private String submitMethods = "get,post,put,delete,patch";
    private Long requestTimeout = 10000L;
  }
}
