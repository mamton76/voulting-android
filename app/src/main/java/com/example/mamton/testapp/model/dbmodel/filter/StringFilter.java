package com.example.mamton.testapp.model.dbmodel.filter;

import com.example.mamton.testapp.model.dbmodel.ColumnMetaInfo;

import java.util.Arrays;
import java.util.List;

public class StringFilter implements Filter {
    private ColumnMetaInfo meta;
    private String value;

    @Override
    public String getWhereClause() {
        return " " + meta.getName() + " like '%?%' ";
    }

    @Override
    public List<String> getWhereArguments() {
        return Arrays.asList(value);
    }

}
