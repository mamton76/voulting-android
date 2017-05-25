package com.example.mamton.testapp.model.dbmodel.filter;

import java.util.List;

public interface Filter {

    String getWhereClause();

    List<String> getWhereArguments();
}
