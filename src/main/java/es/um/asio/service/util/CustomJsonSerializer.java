package es.um.asio.service.util;

import java.io.IOException;

import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.SerializationFeature;


public class CustomJsonSerializer<T> extends JsonSerializer<T> {

	@Override
	@Nullable
	public byte[] serialize(String topic, @Nullable T data) {
		if (data == null) {
			return null;
		}
		try {
			// we need to disable this property
			this.objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			
			return this.objectMapper.writeValueAsBytes(data);
		}
		catch (IOException ex) {
			throw new SerializationException("Can't serialize data [" + data + "] for topic [" + topic + "]", ex);
		}
	}
}
