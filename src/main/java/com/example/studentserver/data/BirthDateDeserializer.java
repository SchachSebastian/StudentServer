package com.example.studentserver.data;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BirthDateDeserializer extends StdDeserializer<LocalDateTime> {
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
	protected BirthDateDeserializer() {
		super(LocalDateTime.class);
	}
	@Override
	public LocalDateTime deserialize(JsonParser jsonParser,
	                                 DeserializationContext deserializationContext)
			throws IOException, JacksonException {
		String date = jsonParser.getText();
		try {
			/*
			zoneID wird benötigt zum erstellen eines LocalDateTime Objekts
			 */
			return LocalDateTime.ofInstant(simpleDateFormat.parse(date).toInstant(),
			                               ZoneId.systemDefault());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
