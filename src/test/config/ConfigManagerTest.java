package config;

import io.github.asbyx.autonomousstreamset.config.ConfigManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigManagerTest {
	@Test
	public void test() {
		ConfigManager.writeConfig("000000000000000000000000000000000000", "pound-2022", "ultimate-singles");

		String[] config = ConfigManager.readConfig();
		assertEquals("000000000000000000000000000000000000", config[0]);
		assertEquals("pound-2022", config[1]);
		assertEquals("ultimate-singles", config[2]);
	}
}
