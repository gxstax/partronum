package com.ant.partronum.rule.datasource;

import com.ant.partronum.rule.RuleConfig;
import com.ant.partronum.rule.parser.JsonRuleConfigParser;
import com.ant.partronum.rule.parser.RuleConfigParser;
import com.ant.partronum.rule.parser.YamlRuleConfigParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>
 * 基于文件格式的数据源
 * </p>
 *
 * @author GaoXin
 * @since 2021/9/25 6:38 下午
 */
public class FileRuleConfigSource implements RuleConfigSource {

    private static final Logger log = Logger.getLogger(FileRuleConfigSource.class.getName());

    public static final String API_LIMIT_CONFIG_NAME = "ratelimiter-rule";
    public static final String YAML_EXTENSION = "yaml";
    public static final String YML_EXTENSION = "yml";
    public static final String JSON_EXTENSION = "json";


    private static final String[] SUPPORT_EXTENSIONS = new String[] {
            YAML_EXTENSION, YML_EXTENSION, JSON_EXTENSION
    };
    private static final Map<String, RuleConfigParser> PARSER_MAP = new HashMap<>();
    static {
        PARSER_MAP.put(YAML_EXTENSION, new YamlRuleConfigParser());
        PARSER_MAP.put(YML_EXTENSION, new YamlRuleConfigParser());
        PARSER_MAP.put(JSON_EXTENSION, new JsonRuleConfigParser());
    }

    @Override
    public RuleConfig load() {
        for (String extension : SUPPORT_EXTENSIONS) {
            InputStream in = null;
            try {
                in = this.getClass().getResourceAsStream("/" + getFileNameByExt(extension));
                if (in != null) {
                    RuleConfigParser parser = PARSER_MAP.get(extension);
                    return parser.parse(in);
                }
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        log.warning("close file error:{}" + e);
                    }
                }
            }
        }
        return null;
    }

    private String getFileNameByExt(String extension) {
        return API_LIMIT_CONFIG_NAME + "." + extension;
    }

}
