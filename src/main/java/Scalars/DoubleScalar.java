package Scalars;

import com.google.gson.Gson;
import graphql.schema.*;
import org.springframework.stereotype.Component;

@Component
public class DoubleScalar extends GraphQLScalarType {
    public DoubleScalar() {
        super("DoubleScalar", "Scalar for double", new Coercing() {
            Gson gson=new Gson();
            @Override
            public Object serialize(Object o) throws CoercingSerializeException {
                return gson.toJson(o, Double.class);
            }

            @Override
            public Object parseValue(Object o) throws CoercingParseValueException {
                if(o instanceof String){
                    return gson.fromJson((String) o,Double.class);
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
