
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.Hacker;
import repositories.HackerRepository;

@Component
@Transactional
public class StringToHackerConverter implements Converter<String, Hacker> {

	@Autowired
	HackerRepository hackerRepository;


	@Override
	public Hacker convert(String text) {
		Hacker result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.hackerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
