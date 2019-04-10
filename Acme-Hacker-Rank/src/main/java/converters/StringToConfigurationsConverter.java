
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.Configurations;
import repositories.ConfigurationsRepository;

@Component
@Transactional
public class StringToConfigurationsConverter implements Converter<String, Configurations> {

	@Autowired
	ConfigurationsRepository	configurationsRepository;


	@Override
	public Configurations convert(final String text) {
		Configurations result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.configurationsRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
