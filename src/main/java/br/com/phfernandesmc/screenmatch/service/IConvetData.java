package br.com.phfernandesmc.screenmatch.service;

public interface IConvetData {
    <T> T getData(String json, Class<T> classes);
}
