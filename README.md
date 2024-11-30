# 开发指南

## 1. common:

**Result**: 定义请求返回的响应

**ResponseCodeConstants**: 定义请求返回的状态码

## 2. controller

### (1) GraphragController: 

**uploadTxtFile**: POST请求上传文件

**invokeGraphRAG**: GET请求调用GraphRAG

需要调用GraphragService的两个服务: invokeGraphRAG(调用GraphRAG), restoreResultToNeo4j(将结果转存Neo4j)

**queryGraphRAG**: GET请求对GraphRAG做查询

## 3. service

### (1) GraphragService:

**GraphRAG部署的流程参考：https://juejin.cn/post/7397291768234000403?share_token=3BD133B5-2D0C-4BA7-A297-9502BD01260E**


**graphragRoot**: 

在resources/application.yaml 中配置GraphRAG项目的根目录，在该service的其它服务中使用这个路径

**neo4jRoot**:

在resources/application.yaml 中配置Neo4j的根目录，在该service的其它服务中使用这个路径


**uploadTxtFile**: 

将文档放在 graphragRoot 下的 input/ 目录下

**invokeGraphRAG**: 

如果是第一次，需要初始化，在 graphragRoot 下执行 poetry run poe index --init --root .

在 graphragRoot 下执行 python -m graphrag.index --root .

**restoreResultToNeo4j**: 

将 graphragRoot/时间戳/artifacts 下的所有 parquet 文件转换成 csv 文件，并将这些 csv 文件拷贝到 neo4jRoot 下的 import/ 目录

将 parquet 文件转换为 csv 文件这个步骤可由一个 resources/scripts/parquet2csv.py 这个脚本完成，只需要修改文件路径

使用 resources/cypher/load.cypher 中的语句，可将 neo4jRoot 下的 import/ 目录下的 csv 文件加载到 Neo4j 中

load.pycher 中的 **第八步只需执行一次** 

**queryGraphRAG**: 

在 graphragRoot 下执行 

poetry run poe query --root . --method global "本文主要讲了什么" 进行全局查询

poetry run poe query --root . --method local "本文主要讲了什么" 进行局部查询









