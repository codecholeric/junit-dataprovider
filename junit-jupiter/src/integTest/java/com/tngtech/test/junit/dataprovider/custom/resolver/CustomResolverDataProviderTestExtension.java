package com.tngtech.test.junit.dataprovider.custom.resolver;

import static java.util.Arrays.asList;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.ReflectionSupport;

import com.tngtech.junit.dataprovider.DataProvider;
import com.tngtech.junit.dataprovider.DisplayNameContext;
import com.tngtech.junit.dataprovider.UseDataProviderInvocationContextProvider;
import com.tngtech.junit.dataprovider.convert.ConverterContext;
import com.tngtech.junit.dataprovider.resolver.DataProviderResolverContext;

class CustomResolverDataProviderTestExtension
        extends UseDataProviderInvocationContextProvider<CustomResolverDataProviderTest, DataProvider> {

    CustomResolverDataProviderTestExtension() {
        super(CustomResolverDataProviderTest.class, DataProvider.class);
    }

    @Override
    protected DataProviderResolverContext getDataProviderResolverContext(ExtensionContext extensionContext,
            CustomResolverDataProviderTest testAnnotation) {
        return new DataProviderResolverContext(extensionContext.getRequiredTestMethod(),
                asList(testAnnotation.resolver()), testAnnotation.resolveStrategy(), asList(testAnnotation.location()),
                DataProvider.class, testAnnotation.value());
    }

    @Override
    protected ConverterContext getConverterContext(DataProvider dataProvider) {
        return new ConverterContext(ReflectionSupport.newInstance(dataProvider.objectArrayConverter()),
                ReflectionSupport.newInstance(dataProvider.singleArgConverter()),
                ReflectionSupport.newInstance(dataProvider.stringConverter()), dataProvider.splitBy(),
                dataProvider.convertNulls(), dataProvider.trimValues(), dataProvider.ignoreEnumCase());
    }

    @Override
    protected DisplayNameContext getDisplayNameContext(DataProvider dataProvider) {
        return new DisplayNameContext(dataProvider.format(), getDefaultPlaceholders());
    }
}
