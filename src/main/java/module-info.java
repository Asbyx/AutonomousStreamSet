module io.github.asbyx.autonomousstreamset {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires javafx.graphics;

	opens io.github.asbyx.autonomousstreamset to javafx.fxml;
	exports io.github.asbyx.autonomousstreamset;
}