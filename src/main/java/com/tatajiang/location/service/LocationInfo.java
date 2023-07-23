package com.tatajiang.location.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tatajiang.location.domain.NodeTree;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


@Component
public class LocationInfo {
    public NodeTree loadFileData() {
        URL resourceUrl = ResourceUtil.getResource("static/level.json");
        JSONArray readJSON = JSONUtil.readJSONArray(new File(resourceUrl.getPath()), Charset.defaultCharset());
        NodeTree nodeTree = new NodeTree();
        nodeTree.setCode("000000");
        nodeTree.setName("中国");
        nodeTree.setProvince("00");
        List<NodeTree> childrenNode = new ArrayList<>();
        for (Object o : readJSON) {
            JSONObject jo = (JSONObject) o;
            NodeTree bean = JSONUtil.toBean(jo, NodeTree.class);
            childrenNode.add(bean);
        }
        nodeTree.setChildren(childrenNode);
        return nodeTree;
    }

    // 递归查找指定 code 的区县局信息及完整路径
    public List<NodeTree> findNodeAndPathByCode(NodeTree rootNode, String targetCode) {
        List<NodeTree> path = new ArrayList<>();
        findNodeAndPathByCodeHelper(rootNode, targetCode, path);
        return path;
    }

    private boolean findNodeAndPathByCodeHelper(NodeTree currentNode, String targetCode, List<NodeTree> path) {
        if (currentNode.getCode().equals(targetCode)) {
            path.add(currentNode);
            return true;
        }
        if (currentNode.getChildren() != null) {
            path.add(currentNode);
            for (NodeTree child : currentNode.getChildren()) {
                if (findNodeAndPathByCodeHelper(child, targetCode, path)) {
                    return true;
                }
            }
            path.remove(path.size() - 1);
        }
        return false;
    }

    public String searchLocationName(String targetCode) {
        NodeTree nodeTree = loadFileData();
        List<NodeTree> path = findNodeAndPathByCode(nodeTree, targetCode);
        StringBuilder locationInfo = new StringBuilder();
        if (!path.isEmpty()) {
            for (NodeTree node : path) {
                locationInfo.append(node.getName());
            }
        } else {
            locationInfo.append("中国湖南省长沙市岳麓区");
        }
        return locationInfo.substring(2);
    }

}
