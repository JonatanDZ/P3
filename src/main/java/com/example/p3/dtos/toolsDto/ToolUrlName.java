package com.example.p3.dtos.toolsDto;
//"record"
//https://www.geeksforgeeks.org/java/what-are-java-records-and-how-to-use-them-alongside-constructors-and-methods/
//Used for returning only necessary value of a tool for tags
public record ToolUrlName (String name, String url, Boolean is_dynamic){}