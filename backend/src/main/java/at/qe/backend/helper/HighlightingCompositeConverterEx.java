package at.qe.backend.helper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * This class is used to change the color of the log output.
 * It is being used in the logback.xml file.
 * Source: https://github.com/shuwada/logback-custom-color
 **/
public class HighlightingCompositeConverterEx extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        switch (level.toInt()) {
            case Level.ERROR_INT:
                return ANSIConstants.BOLD + ANSIConstants.RED_FG; // same as default color scheme
            case Level.WARN_INT:
                return ANSIConstants.YELLOW_FG;// same as default color scheme
            case Level.INFO_INT:
                return ANSIConstants.CYAN_FG; // use CYAN instead of BLUE
            default:
                return ANSIConstants.GREEN_FG;
        }
    }
}
