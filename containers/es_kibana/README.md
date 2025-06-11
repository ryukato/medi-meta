# Elasticsearch and Kibana
## Reference
* https://www.elastic.co/kr/blog/getting-started-with-the-elastic-stack-and-docker-compose

## Run containers
Please run shell scripts - `run-es-kibana-on-local.sh`

## Install nori plugin
* First run `bash` on the elasticsearch container by running following command
```shell
docker exec -it [container id or name] /bin/bash
```

> Note
> 
> On Windows, please run `docker exec -it es_kibana-es01-1 //bin//bash`. 
> But if you have below error message, then you need to run below to execute bash on container. 
> error message: `the input device is not a TTY.  If you are using mintty, try prefixing the command with 'winpty'`

```shell
winpty docker exec -it 05c61d471c33 bash
```

```shell
bin/elasticsearch-plugin install analysis-nori
```

## How to use
### Elasticsearch API
#### Host and port
* host: localhost
* port: 9200

#### Query cluster
##### On Windows
* open `GitBash` with administrator
* run following commands
```shell
curl --ssl-revoke-best-effort --cacert [project path]/containers/es_kibana/cert/ca.crt -u elastic:elastic "https://localhost:9200"
```

> Note
> 
> Please replace `project path` with your absolute path of the project directory. And the path has to start with `/[drive name]` like `/c`

##### On Mac or others
* open terminal and run below
```shell
curl --ssl-revoke-best-effort --cacert [project path]/containers/es_kibana/cert/ca.crt -u elastic:elastic "https://localhost:9200"
```

### Kibana
* open browser and go to "http://localhost:5601"
* login with elastic/elastic

## Config index for product
Open kibana with your browser, then go to "Dev Tools > Console". And then please run scripts in following orders.
1. `PUT _component_template/general_ngram_analyzer`
   * body: ./templates/general-ngram-analyzer-component-template.json
2. `PUT _component_template/korean_ngram_analyzer`
    * body: ./templates/korean-ngram-analyzer-component-template.json
3. `PUT _index_template/product`
    * body: ./templates/product-index-template.json

## Run sample query
### Create sample product
* Run below command on kibana's dev-tool console
  * ```
    POST /product/_doc/
    {
    "code": "P100000001",
    "name": "삭센다펜주6밀리그램/밀리리터(리라글루티드)",
    "name_en": "Saxenda Pen Inj. 6 mg/mL(Liraglutide)",
    "main_ingredient": "[M266520]리라글루티드"
    }
    ```
### Query by name
* Run below command on kibana's dev-tool console
  * ```
    GET product/_search
    {
        "query": {
            "multi_match": {
                "query": "삭센",
                "fields": ["name"]
            }
        }
    }
    ```
### Query by main_ingredient
* Run below command on kibana's dev-tool console
    * ```
      GET product/_search
      {
          "query": {
              "multi_match": {
                  "query": "리라",
                  "fields": ["main_ingredient"]            
              }
          }
      }
      ```
