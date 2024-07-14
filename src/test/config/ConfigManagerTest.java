package config;

import io.github.asbyx.autonomousstreamset.config.ConfigManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigManagerTest {
	@Test
	public void test() {
		ConfigManager.writeConfig("myawesomestream", "000000000000000000000000000000000000", "pound-2022", "ultimate-singles");

		String[] config = ConfigManager.readConfig();
		assert config != null;
		assertEquals(4, config.length);
		assertEquals("myawesomestream", config[0]);
		assertEquals("000000000000000000000000000000000000", config[1]);
		assertEquals("pound-2022", config[2]);
		assertEquals("ultimate-singles", config[3]);
	}
}
