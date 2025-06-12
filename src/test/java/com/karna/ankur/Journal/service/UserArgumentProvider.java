//package com.karna.ankur.Journal.service;
//
//import com.karna.ankur.Journal.Entity.UserEntity;
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.ArgumentsProvider;
//
//import java.util.stream.Stream;
//
//public class UserArgumentProvider implements ArgumentsProvider {
//    @Override
//    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
//        return Stream.of(
//                Arguments.of(UserEntity.builder().userName("hg5").password("ef").build()),
//                Arguments.of(UserEntity.builder().userName("efgrwe").password("fee").build())
//        );
//    }
//}
