#!/usr/bin/env groovy

// import groovy.yaml.YamlBuilder

def call(String caption = 'Title') {
  echo "---------------- ${caption} ----------------"
  //def yaml = new YamlBuilder()
  //yaml('---')
  //yaml('apiVersion: apps/v1')
  println(yaml)
}
