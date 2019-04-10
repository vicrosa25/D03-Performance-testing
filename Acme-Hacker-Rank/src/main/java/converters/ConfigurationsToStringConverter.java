
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Configurations;

@Component
@Transactional
public class ConfigurationsToStringConverter implements Converter<Configurations, String> {

	@Override
	public String convert(final Configurations configuration) {
		String result;
		if (configuration == null)
			result = null;
		else
			result = String.valueOf(configuration.getId());
		return result;
	}

}
