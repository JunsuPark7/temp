package jndc.jndcorg.dialog;

import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.tool.schema.extract.spi.SequenceInformationExtractor;

public class AppOracleDialect extends Oracle12cDialect {
    @Override
    public SequenceInformationExtractor getSequenceInformationExtractor() {
        return AppSequenceInformationExtractor.INSTANCE;
    }

    @Override
    public String getQuerySequencesString() {
        return "select * from user_sequences";
    }
}
