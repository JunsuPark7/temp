package jndc.jndcorg.dialog;

import org.hibernate.tool.schema.extract.internal.SequenceInformationExtractorOracleDatabaseImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppSequenceInformationExtractor extends SequenceInformationExtractorOracleDatabaseImpl {
    /**
     * Singleton access
     */
    public static final AppSequenceInformationExtractor INSTANCE = new AppSequenceInformationExtractor();

    @Override
    protected Long resultSetMinValue(ResultSet resultSet) throws SQLException {
        return resultSet.getBigDecimal(super.sequenceMinValueColumn()).longValue();
    }
}
