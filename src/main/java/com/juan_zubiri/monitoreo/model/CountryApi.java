package com.juan_zubiri.monitoreo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryApi {

    @JsonProperty("name")
    private Map<String, Object> name;  //manejar los diferentes nombres y sub-nombres

    @JsonProperty("flags")
    private Flag flags;  //  deserializar las banderas

    // obtener el nombre común
    public String getCommonName() {
        if (name != null && name.containsKey("common")) {
            return (String) name.get("common");
        }
        return null;
    }

    public static class Flag {
        private String png;
        private String svg;
        private String alt;

        public String getPng() {
            return png;
        }

        public void setPng(String png) {
            this.png = png;
        }

        public String getSvg() {
            return svg;
        }

        public void setSvg(String svg) {
            this.svg = svg;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }
    }
}
