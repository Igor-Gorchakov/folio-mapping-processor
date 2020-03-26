package org.folio;

import io.vertx.core.json.JsonObject;
import org.apache.commons.io.IOUtils;
import org.folio.processor.RuleProcessor;
import org.folio.reader.FieldReader;
import org.folio.reader.JPathSyntaxReader;
import org.folio.writer.RecordWriter;
import org.folio.writer.impl.JsonRecordWriter;
import org.folio.writer.impl.MarcRecordWriter;
import org.folio.writer.impl.XmlRecordWriter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileReader;
import java.io.IOException;

@RunWith(JUnit4.class)
public class JPathSyntaxRuleTest {
    private static JsonObject entity;
    private static JsonObject rules;

    @BeforeClass
    public static void setup() throws IOException {
        entity = new JsonObject(IOUtils.toString(new FileReader("src/test/resources/given_inventory_instance.json")));
        rules = new JsonObject(IOUtils.toString(new FileReader("src/test/resources/rules/rules_jpath-syntax.json")));
    }

    @Test
    public void shouldMapEntityTo_MarcRecord() throws IOException {
        // given
        RuleProcessor ruleProcessor = new RuleProcessor(rules);
        FieldReader reader = new JPathSyntaxReader(entity);
        RecordWriter writer = new MarcRecordWriter();
        // when
        String actualMarcRecord = ruleProcessor.process(reader, writer);
        // then
        String expectedMarcRecord = IOUtils.toString(new FileReader("src/test/resources/records/expected_marc_record.mrc"));
        Assert.assertEquals(expectedMarcRecord, actualMarcRecord);
    }

    @Test
    public void shouldMapEntityTo_JsonRecord() throws IOException {
        // given
        RuleProcessor ruleProcessor = new RuleProcessor(rules);
        FieldReader reader = new JPathSyntaxReader(entity);
        RecordWriter writer = new JsonRecordWriter();
        // when
        String actualJsonRecord = ruleProcessor.process(reader, writer);
        // then
        String expectedJsonRecord = IOUtils.toString(new FileReader("src/test/resources/records/expected_json_record.json"));
        Assert.assertEquals(expectedJsonRecord, actualJsonRecord);
    }

    @Test
    public void shouldMapEntityTo_XmlRecord() throws IOException {
        // given
        RuleProcessor ruleProcessor = new RuleProcessor(rules);
        FieldReader fieldReader = new JPathSyntaxReader(entity);
        RecordWriter writer = new XmlRecordWriter();
        // when
        String actualXmlRecord = ruleProcessor.process(fieldReader, writer);
        // then
        String expectedXmlRecord = IOUtils.toString(new FileReader("src/test/resources/records/expected_xml_record.xml"));
        Assert.assertEquals(expectedXmlRecord, actualXmlRecord);
    }
}