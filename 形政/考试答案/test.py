import json

with open('./fs2021.json', encoding='utf-8') as f:
    line = f.read()
    dicts = json.loads(line)

query_dict = {}
for dic in dicts:
    title = dic['title']
    answer = dic['rightAnswer']
    choice = dic['answerChoice']
    answer_list = eval(answer)
    choice_list = eval(choice)
    answer_label_list = [cho['label']
                         for cho in choice_list if cho['value'][0]
                         in answer_list]
    query_dict[title] = answer_label_list

while True:
    keyword = input("输入关键字：")
    print()
    proper_list = [t+'\n'+''.join(query_dict[t])+'\n'
                   for t in query_dict.keys() if keyword in t]
    print('\n'.join(proper_list))
