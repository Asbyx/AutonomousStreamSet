module io.github.asbyx.autonomousstreamset {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires javafx.graphics;
	requires org.json;
	requires okhttp3;

	opens io.github.asbyx.autonomousstreamset to javafx.fxml;
	exports io.github.asbyx.autonomousstreamset;
}