package scalars;

import com.google.gson.Gson;
import graphql.schema.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateScalar extends GraphQLScalarType{
    public DateScalar() {

        super("DateScalar", "custom date", new Coercing() {
            Gson gson=new Gson();
            @Override
            public Object serialize(Object o) throws CoercingSerializeException {
                return gson.toJson(o, Date.class);
            }

            @Override
            public Object parseValue(Object o) throws CoercingParseValueException {
                if(o instanceof String){
                    return gson.fromJson((String) o,Date.class);
                }
                throw new CoercingParseValueException("input not a string");
            }

            @Override
            public Object parseLiteral(Object o) throws CoercingParseLiteralException {
                return null;
            }
        });
    }
}
