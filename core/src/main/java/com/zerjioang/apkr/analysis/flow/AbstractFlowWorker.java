package com.zerjioang.apkr.analysis.flow;

import apkr.external.modules.controlflow.model.NodeConnection;
import apkr.external.modules.controlflow.model.base.AbstractAtomNode;
import apkr.external.modules.controlflow.model.map.base.AbstractFlowMap;
import apkr.external.modules.controlflow.model.nodes.FieldNode;
import apkr.external.modules.controlflow.model.nodes.MethodNode;
import apkr.external.modules.controlflow.model.nodes.NormalNode;
import com.zerjioang.apkr.analysis.dynamicscan.machine.base.AbstractDVMThread;
import com.zerjioang.apkr.analysis.dynamicscan.machine.base.DalvikVM;
import com.zerjioang.apkr.analysis.dynamicscan.machine.base.struct.generic.IAtomField;
import com.zerjioang.apkr.analysis.dynamicscan.machine.base.struct.generic.IAtomFrame;
import com.zerjioang.apkr.analysis.dynamicscan.machine.base.struct.generic.IAtomMethod;
import com.zerjioang.apkr.analysis.dynamicscan.machine.inst.Instruction;
import com.zerjioang.apkr.sdk.model.base.ApkrProject;

/**
 * Created by .local on 07/10/2016.
 */
public abstract class AbstractFlowWorker extends AbstractDVMThread {

    private final static NodeGenerator generator = NodeGenerator.getInstance();
    protected static AbstractFlowMap flowMap;
    protected AbstractAtomNode fromNode;
    protected AbstractAtomNode toNode;

    public AbstractFlowWorker(DalvikVM vm, ApkrProject currentProject) {
        super(vm, currentProject);
    }

    protected final MethodNode buildMethodNode(Instruction inst, IAtomFrame frame, IAtomMethod method) {
        return generator.buildMethodNode(flowMap, inst, frame, method);
    }

    protected final NormalNode builNormalNode(Instruction currentInstruction) {
        return generator.builNormalNode(flowMap, currentInstruction);
    }

    protected final NormalNode builNormalNode(Instruction currentInstruction, String key, String value) {
        return generator.builNormalNode(flowMap, currentInstruction, key, value);
    }

    protected final FieldNode buildFieldNode(Instruction inst, IAtomField field, int pc) {
        return generator.buildFieldNode(flowMap, inst, field, pc);
    }

    protected final void createNewConnection(AbstractAtomNode from, AbstractAtomNode to, Instruction currentInstruction) {
        from = flowMap.addNode(from);
        to = flowMap.addNode(to);
        //avoid connections with itself
        if (true || !from.getConnectionLabel().equals(to.getConnectionLabel())) {
            NodeConnection conn = new NodeConnection(from, to, currentInstruction.description());
            flowMap.addConnection(conn);
        }
    }
}
