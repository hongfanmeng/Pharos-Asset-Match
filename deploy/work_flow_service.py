# 接收requirement并返回workflow
from flask import Flask, request,jsonify
# from work_flow import WorkFlow

app = Flask(__name__)

@app.route('/requirement', methods=['POST'])
def receive_data():
    data=request.get_json()
    # requirement = {
    #     "id": data['id'],
    #     "requirement": data['requirement'],
    #     "description": data.get('description', ""),
    #     "longitude": float(data['longitude']),
    #     "latitude": float(data['latitude']),
    #     "company": data.get('company', "未知公司"),
    #     "tasks": data.get('tasks', [])
    # }
    requirement = {
        "id": data.get('id'),
        "requirement": data.get('requirement'),
        "description": data.get('description', ""),
        "longitude": float(data.get('longitude')),
        "latitude": float(data.get('latitude')),
        "company": data.get('company', "未知公司"),
        "tasks": data.get('tasks', [])
    }
    print(requirement)
    work_flow=generate_task(requirement)
    print(work_flow)
    return jsonify(work_flow.get_wf())

def generate_task(requirement):

    workflow=WorkFlow(requirement)
    workflow.generate()
    return workflow




# work_flow.py
meta_tasks = [
    {
        "id": "taskReconnaissance",
        "leasingCompany": "A",
        "capId": 1,
        "requirementId": "rid"
    },
    {
        "id": "taskIdentify",
        "leasingCompany": "B",
        "capId": 2,
        "requirementId": "rid"
    },
    {
        "id": "taskNavigate",
        "leasingCompany": "C",
        "capId": 3,
        "requirementId": "rid"
    }
]


work_flow_init = {
    "wf_id": '100',
    "startEvent": {
        "id": "startEvent",
        "name": "开始工作"
    },

    "tasks": meta_tasks,

    "endEvent": {
        "id": "endEvent",
        "name": "终止工作"
    },

    "sequenceFlows": [
        {
            "id": "flow1",
            "sourceRef": "startEvent",
            "targetRef": "taskReconnaissance",
        },
        {
            "id": "flow2",
            "sourceRef": "taskReconnaissance",
            "targetRef": "taskIdentify",
        },
        #
        {
            "id": "flow3",
            "sourceRef": "taskIdentify",
            "targetRef": "taskNavigate",
            "location":
                {
                    "longitude": 100.0,
                    "latitude": 100.0
                },
        },
        # {
        #     "id": "loopFlow",
        #     "sourceRef": "taskNavigate",
        #     "targetRef": "taskIdentify",
        #     "conditionExpression": "未引导到目的地"
        # },
        {
            "id": "finalFlow",
            "sourceRef": "taskNavigate",
            "targetRef": "endEvent",
            "conditionExpression": "已引导到目的地",
            "location":
                {
                    "longitude": 100.0,
                    "latitude": 100.0
                },
        }
    ]
}

class WorkFlow:
    def __init__(self,requirement):
        self.requirement=requirement
        for task in meta_tasks:
            task["requirementId"] = self.requirement["id"]
        work_flow_init["tasks"] = meta_tasks
        self.work_flow={}

    def generate(self,exist=True):
        if exist==True:

            self.work_flow=work_flow_init
            self.work_flow['wf_id']=self.requirement['id']
            self.work_flow["sequenceFlows"][-1]["location"]["longitude"]=self.requirement["longitude"]
            self.work_flow["sequenceFlows"][-1]["location"]["longitude"] = self.requirement["longitude"]

    def get_wf(self):
        return self.work_flow



if __name__=="__main__":
    app.run(host='0.0.0.0', debug=True)
